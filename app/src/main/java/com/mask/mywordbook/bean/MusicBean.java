package com.mask.mywordbook.bean;

public class MusicBean {
    /**
     * code : 1
     * data : {"name":"Yummy","url":"http://music.163.com/song/media/outer/url?id=1413988070.mp3","picurl":"http://p1.music.126.net/R5hmBoVZt0I56BPiVic1aA==/109951164601146483.jpg","artistsname":"Justin Bieber"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : Yummy
         * url : http://music.163.com/song/media/outer/url?id=1413988070.mp3
         * picurl : http://p1.music.126.net/R5hmBoVZt0I56BPiVic1aA==/109951164601146483.jpg
         * artistsname : Justin Bieber
         */

        private String name;
        private String url;
        private String picurl;
        private String artistsname;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getArtistsname() {
            return artistsname;
        }

        public void setArtistsname(String artistsname) {
            this.artistsname = artistsname;
        }
    }
}
