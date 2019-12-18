package com.huijiewei.agile.spring.upload;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UploadResponse {
    private File original;

    private List<Thumb> thumbs;

    public void addThumb(String thumb, String name, String url) {
        if (this.thumbs == null) {
            this.thumbs = new ArrayList<>();
        }

        this.thumbs.add(new Thumb(thumb, name, url));
    }

    @Getter
    @Setter
    public static class Thumb extends File {
        private String thumb;

        public Thumb(String thumb, String name, String url) {
            super(name, url);
            this.setThumb(thumb);
        }
    }

    @Getter
    @Setter
    public static class File {
        private String name;
        private String url;

        public File(String name, String url) {
            this.setName(name);
            this.setUrl(url);
        }
    }
}
