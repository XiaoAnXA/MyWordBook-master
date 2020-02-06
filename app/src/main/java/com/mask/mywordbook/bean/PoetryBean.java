package com.mask.mywordbook.bean;

/**
 * 随机单句诗词
 */
public class PoetryBean {

    /**
     * code : 200
     * message : 成功!
     * result : {"author":"李之仪","origin":"南乡子·端午","category":"古诗文-天气-写雨","content":"小雨湿黄昏。重午佳辰独掩门。"}
     */

    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        /**
         * author : 李之仪
         * origin : 南乡子·端午
         * category : 古诗文-天气-写雨
         * content : 小雨湿黄昏。重午佳辰独掩门。
         */

        private String author;
        private String origin;
        private String category;
        private String content;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
