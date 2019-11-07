package com.huijiewei.agile.base.admin.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminGroupTest {
    @Test
    public void whenUseJsonViewToSerialize_thenCorrect()
            throws JsonProcessingException {

        AdminGroup adminGroup = new AdminGroup();
        adminGroup.setId(1);
        adminGroup.setName("管理员");

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String result = mapper
                .writerWithView(AdminGroup.Views.Create.class)
                .writeValueAsString(adminGroup);

        assertThat(result).isEqualTo("");
    }
}