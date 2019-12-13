package com.huijiewei.agile.serve.admin.controller;

import com.github.javafaker.Faker;
import com.huijiewei.agile.core.entity.Captcha;
import com.huijiewei.agile.core.exception.BadRequestException;
import com.huijiewei.agile.core.repository.CaptchaRepository;
import com.huijiewei.agile.core.until.HttpUtils;
import com.huijiewei.agile.core.user.entity.User;
import com.huijiewei.agile.core.user.repository.UserRepository;
import com.huijiewei.agile.spring.upload.ImageCropRequest;
import com.huijiewei.agile.spring.upload.UploadDriver;
import com.huijiewei.agile.spring.upload.UploadResponse;
import com.wf.captcha.GifCaptcha;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RestController
@Tag(name = "open", description = "开放接口")
public class OpenController {
    private final UserRepository userRepository;
    private final UploadDriver uploadDriver;
    private final CaptchaRepository captchaRepository;

    @Autowired
    public OpenController(UserRepository userRepository, UploadDriver uploadDriver, CaptchaRepository captchaRepository) {
        this.userRepository = userRepository;
        this.uploadDriver = uploadDriver;
        this.captchaRepository = captchaRepository;
    }

    @PostMapping(
            value = "/open/upload-file",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public UploadResponse actionUploadFile(@RequestParam("policy") String policy, @RequestPart("file") MultipartFile file) {
        return this.uploadDriver.upload(policy, file);
    }

    @PostMapping(
            value = "/open/crop-image",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public UploadResponse actionCropImage(@RequestParam("policy") String policy, @RequestBody ImageCropRequest request) {
        return this.uploadDriver.crop(policy, request);
    }

    @GetMapping(
            value = "/open/captcha"
    )
    public String actionCaptcha(@RequestParam("uuid") String uuid, HttpServletRequest servletRequest) {
        if (StringUtils.isEmpty(uuid)) {
            throw new BadRequestException("参数错误");
        }

        Captcha captcha = new Captcha();
        captcha.setUuid(uuid);
        captcha.setUserAgent(HttpUtils.getUserAgent(servletRequest));
        captcha.setRemoteAddr(HttpUtils.getRemoteAddr(servletRequest));

        GifCaptcha gifCaptcha = new GifCaptcha(100, 30, 5);

        captcha.setCode(gifCaptcha.text());

        captchaRepository.save(captcha);

        return gifCaptcha.toBase64();
    }

    @GetMapping(
            value = "/open/fake-user",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public void actionFakeUser(Integer count) {
        Faker englishFaker = new Faker(Locale.ENGLISH);
        Faker chineseFaker = new Faker(Locale.CHINA);

        Optional<User> lastUserOptional = this.userRepository.findFirstByOrderByIdDesc();

        long fakeCreatedAtTimestamp = lastUserOptional.map(user -> user.getCreatedAt().toEpochSecond(ZoneOffset.of("+8"))).orElseGet(() -> LocalDateTime.of(2019, 5, 1, 12, 20, 32).toEpochSecond(ZoneOffset.of("+8")));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        List<String> createdFromList = new ArrayList<>();
        createdFromList.add(User.CREATED_FROM_WEB);
        createdFromList.add(User.CREATED_FROM_APP);
        createdFromList.add(User.CREATED_FROM_WECHAT);
        createdFromList.add(User.CREATED_FROM_SYSTEM);

        for (int i = 0; i < count; i++) {
            String fakePhone = chineseFaker.phoneNumber().cellPhone();

            if (this.userRepository.existsByPhone(fakePhone)) {
                continue;
            }

            String fakeEmail = englishFaker.internet().emailAddress();

            if (this.userRepository.existsByEmail(fakeEmail)) {
                continue;
            }

            Collections.shuffle(createdFromList);

            User user = new User();
            user.setPhone(fakePhone);
            user.setEmail(fakeEmail);
            user.setPassword(passwordEncoder.encode(englishFaker.internet().password()));
            user.setName(chineseFaker.name().fullName());
            user.setCreatedIp(chineseFaker.internet().ipV4Address());
            user.setCreatedFrom(createdFromList.get(0));

            fakeCreatedAtTimestamp += new Random().nextInt((28800 - 30) + 1) + 30;

            LocalDateTime fakeCreatedAt = LocalDateTime.ofEpochSecond(
                    fakeCreatedAtTimestamp,
                    new Random().nextInt((28800 - 30) + 1) + 30,
                    ZoneOffset.of("+8")
            );

            user.setCreatedAt(fakeCreatedAt);

            int randomInt = new Random().nextInt((100 - 1) + 1) + 1;

            if (randomInt > 9) {
                int randomAvatarId = new Random().nextInt((100677 - 100001) + 1) + 100001;
                String randomAvatar = Integer.toString(randomAvatarId).substring(1);

                user.setAvatar("https://yuncars-other.oss-cn-shanghai.aliyuncs.com/boilerplate/avatar/a" + randomAvatar + ".png@!avatar");
            }

            this.userRepository.save(user);
        }
    }
}
