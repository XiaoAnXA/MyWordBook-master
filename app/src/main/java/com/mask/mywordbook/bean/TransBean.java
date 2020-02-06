package com.mask.mywordbook.bean;

import java.util.List;

/**
 * 中英文翻译
 */
public class TransBean {

    /**
     * tSpeakUrl : http://openapi.youdao.com/ttsapi?q=like&langType=en&sign=589CDADF6516A719F5AC72979251D60C&salt=1544848846951&voice=4&format=mp3&appKey=6e85ed4b6946b08c
     * web : [{"value":["be fond of","Like","care for","take to"],"key":"喜欢"},{"value":["dislike","hate","objection","loathe"],"key":"不喜欢"},{"value":["take to","acquire a taste for","Starts to like","Start like"],"key":"开始喜欢"}]
     * query : 喜欢
     * translation : ["like"]
     * errorCode : 0
     * dict : {"url":"yddict://m.youdao.com/dict?le=eng&q=%E5%96%9C%E6%AC%A2"}
     * webdict : {"url":"http://m.youdao.com/dict?le=eng&q=%E5%96%9C%E6%AC%A2"}
     * basic : {"explains":["be","like","love","be","enjoy"]}
     * l : zh-CHS2EN
     * speakUrl : http://openapi.youdao.com/ttsapi?q=%E5%96%9C%E6%AC%A2&langType=zh-CHS&sign=DDADA74349A3A92268A72B155B3CA984&salt=1544848846951&voice=4&format=mp3&appKey=6e85ed4b6946b08c
     */

    private String tSpeakUrl;
    private String query;
    private String errorCode;
    private DictBean dict;
    private WebdictBean webdict;
    private BasicBean basic;
    private String l;
    private String speakUrl;
    private List<WebBean> web;
    private List<String> translation;

    public String getTSpeakUrl() {
        return tSpeakUrl;
    }

    public void setTSpeakUrl(String tSpeakUrl) {
        this.tSpeakUrl = tSpeakUrl;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public DictBean getDict() {
        return dict;
    }

    public void setDict(DictBean dict) {
        this.dict = dict;
    }

    public WebdictBean getWebdict() {
        return webdict;
    }

    public void setWebdict(WebdictBean webdict) {
        this.webdict = webdict;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public static class DictBean {
        /**
         * url : yddict://m.youdao.com/dict?le=eng&q=%E5%96%9C%E6%AC%A2
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class WebdictBean {
        /**
         * url : http://m.youdao.com/dict?le=eng&q=%E5%96%9C%E6%AC%A2
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class BasicBean {
        private List<String> explains;

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        /**
         * value : ["be fond of","Like","care for","take to"]
         * key : 喜欢
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
