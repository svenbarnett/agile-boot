package com.huijiewei.agile.spring.upload.driver;

import com.huijiewei.agile.spring.upload.BaseUpload;
import com.huijiewei.agile.spring.upload.UploadRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "agile.spring.upload.aliyun-oss")
public class AliyunOSS extends BaseUpload {
    private String accessKeyId;
    private String accessKeySecret;

    private String endpoint;
    private String bucket;
    private String directory = "";

    @Override
    public UploadRequest build(Integer fileSize, List<String> fileTypes) {
        String url = "https://" + this.bucket + "." + this.endpoint;
        String directory = StringUtils.stripEnd(this.directory, "/") +
                "/" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM")).toString() +
                "/";

        String jsonizedExpiration = String.format("\"expiration\":\"%s\"", LocalDateTime.now().plusMinutes(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString());

        PolicyConditions conditions = new PolicyConditions();
        conditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, fileSize);
        conditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, directory);
        String jsonizedConds = conditions.jsonize();

        String policyJson = String.format("{%s,%s}", jsonizedExpiration, jsonizedConds);

        String policyString = this.base64Encode(policyJson);

        String signature = this.base64Encode(this.hmacSHA1(this.accessKeySecret, policyString));

        Map<String, String> params = new HashMap<>();
        params.put("OSSAccessKeyId", this.accessKeyId);
        params.put("key", directory + "${filename}");
        params.put("policy", policyString);
        params.put("signature", signature);
        params.put("success_action_status", "200");

        UploadRequest request = new UploadRequest();
        request.setUrl(url);
        request.setParams(params);
        request.setHeaders(null);
        request.setDataType("xml");
        request.setParamName(this.paramName());
        request.setImageProcess("?x-oss-process=style/");
        request.setResponseParse("return result.querySelector('PostResponse > Location').textContent;");

        return request;
    }

    @Override
    public String paramName() {
        return "file";
    }

    enum MatchMode {
        Unknown, Exact, // Exact match
        StartWith, // Starts With
        Range // The range of file size
    }

    static class ConditionItem {
        private String name;
        private MatchMode matchMode;
        private String value;
        private TupleType tupleType;
        private long minimum;
        private long maximum;

        ConditionItem(String name, String value) {
            this.matchMode = MatchMode.Exact;
            this.name = name;
            this.value = value;
            this.tupleType = TupleType.Two;
        }

        ConditionItem(String name, long min, long max) {
            this.matchMode = MatchMode.Range;
            this.name = name;
            this.minimum = min;
            this.maximum = max;
            this.tupleType = TupleType.Three;
        }

        ConditionItem(MatchMode matchMode, String name, String value) {
            this.matchMode = matchMode;
            this.name = name;
            this.value = value;
            this.tupleType = TupleType.Three;
        }

        String jsonize() {
            String jsonizedCond = null;
            switch (tupleType) {
                case Two:
                    jsonizedCond = String.format("{\"%s\":\"%s\"},", name, value);
                    break;
                case Three:
                    switch (matchMode) {
                        case Exact:
                            jsonizedCond = String.format("[\"eq\",\"$%s\",\"%s\"],", name, value);
                            break;
                        case StartWith:
                            jsonizedCond = String.format("[\"starts-with\",\"$%s\",\"%s\"],", name, value);
                            break;
                        case Range:
                            jsonizedCond = String.format("[\"content-length-range\",%d,%d],", minimum, maximum);
                            break;
                        default:
                            throw new IllegalArgumentException(String.format("Unsupported match mode %s", matchMode.toString()));
                    }
                    break;
            }

            return jsonizedCond;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MatchMode getMatchMode() {
            return matchMode;
        }

        public void setMatchMode(MatchMode matchMode) {
            this.matchMode = matchMode;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public TupleType getTupleType() {
            return tupleType;
        }

        public void setTupleType(TupleType tupleType) {
            this.tupleType = tupleType;
        }

        public long getMinimum() {
            return minimum;
        }

        public void setMinimum(long minimum) {
            this.minimum = minimum;
        }

        public long getMaximum() {
            return maximum;
        }

        public void setMaximum(long maximum) {
            this.maximum = maximum;
        }

        /**
         * The condition tuple type: currently only supports Two and Three.
         */
        enum TupleType {
            Two, Three
        }
    }

    static class PolicyConditions {
        final static String COND_CONTENT_LENGTH_RANGE = "content-length-range";
        final static String COND_CACHE_CONTROL = "Cache-Control";
        final static String COND_CONTENT_TYPE = "Content-Type";
        final static String COND_CONTENT_DISPOSITION = "Content-Disposition";
        final static String COND_CONTENT_ENCODING = "Content-Encoding";
        final static String COND_EXPIRES = "Expires";
        final static String COND_KEY = "key";
        final static String COND_SUCCESS_ACTION_REDIRECT = "success_action_redirect";
        final static String COND_SUCCESS_ACTION_STATUS = "success_action_status";
        final static String COND_X_OSS_META_PREFIX = "x-oss-meta-";
        final static String COND_X_OSS_SERVER_SIDE_PREFIX = "x-oss-server-side-";

        private static Map<String, List<MatchMode>> _supportedMatchRules = new HashMap<String, List<MatchMode>>();

        static {
            List<MatchMode> ordinaryMatchModes = new ArrayList<MatchMode>();
            ordinaryMatchModes.add(MatchMode.Exact);
            ordinaryMatchModes.add(MatchMode.StartWith);
            List<MatchMode> specialMatchModes = new ArrayList<MatchMode>();
            specialMatchModes.add(MatchMode.Range);

            _supportedMatchRules.put(COND_CONTENT_LENGTH_RANGE, specialMatchModes);

            _supportedMatchRules.put(COND_CACHE_CONTROL, ordinaryMatchModes);
            _supportedMatchRules.put(COND_CONTENT_TYPE, ordinaryMatchModes);
            _supportedMatchRules.put(COND_CONTENT_DISPOSITION, ordinaryMatchModes);
            _supportedMatchRules.put(COND_CONTENT_ENCODING, ordinaryMatchModes);
            _supportedMatchRules.put(COND_EXPIRES, ordinaryMatchModes);

            _supportedMatchRules.put(COND_KEY, ordinaryMatchModes);
            _supportedMatchRules.put(COND_SUCCESS_ACTION_REDIRECT, ordinaryMatchModes);
            _supportedMatchRules.put(COND_SUCCESS_ACTION_STATUS, ordinaryMatchModes);
            _supportedMatchRules.put(COND_X_OSS_META_PREFIX, ordinaryMatchModes);
            _supportedMatchRules.put(COND_X_OSS_SERVER_SIDE_PREFIX, ordinaryMatchModes);
        }

        private List<ConditionItem> _conds = new ArrayList<ConditionItem>();

        public void addConditionItem(String name, String value) {
            checkMatchModes(MatchMode.Exact, name);
            _conds.add(new ConditionItem(name, value));
        }

        void addConditionItem(MatchMode matchMode, String name, String value) {
            checkMatchModes(matchMode, name);
            _conds.add(new ConditionItem(matchMode, name, value));
        }

        void addConditionItem(String name, long min, long max) {
            if (min > max)
                throw new IllegalArgumentException(String.format("Invalid range [%d, %d].", min, max));
            _conds.add(new ConditionItem(name, min, max));
        }

        private void checkMatchModes(MatchMode matchMode, String condName) {
            if (_supportedMatchRules.containsKey(condName)) {
                List<MatchMode> mms = _supportedMatchRules.get(condName);
                if (!mms.contains(matchMode))
                    throw new IllegalArgumentException(
                            String.format("Unsupported match mode for condition item %s", condName));
            }
        }

        String jsonize() {
            StringBuilder jsonizedConds = new StringBuilder();
            jsonizedConds.append("\"conditions\":[");
            for (ConditionItem cond : _conds)
                jsonizedConds.append(cond.jsonize());
            if (_conds.size() > 0)
                jsonizedConds.deleteCharAt(jsonizedConds.length() - 1);
            jsonizedConds.append("]");
            return jsonizedConds.toString();
        }
    }
}
