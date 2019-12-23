package com.huijiewei.agile.spring.upload;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UploadResponse {
    private String original;

    private List<Thumb> thumbs;

    public void addThumb(String thumb, String url) {
        if (this.thumbs == null) {
            this.thumbs = new ArrayList<>();
        }

        this.thumbs.add(new Thumb(thumb, url));
    }

    @Getter
    @Setter
    public static class Thumb {
        private String thumb;
        private String url;

        public Thumb(String thumb, String url) {
            this.setThumb(thumb);
            this.setUrl(url);
        }
    }
}
