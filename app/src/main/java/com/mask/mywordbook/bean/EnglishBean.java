package com.mask.mywordbook.bean;

import java.util.List;

public class EnglishBean {

    /**
     * showapi_res_error :
     * showapi_res_id : 9e41377263e44812b51c49d05cbed5ec
     * showapi_res_code : 0
     * showapi_res_body : {"ret_code":0,"data":[{"english":"I loved you. I leave you. I'll miss you.","chinese":"曾经爱过你。现在我走了。将来，我还会想念你。"}],"ret_message":"Success"}
     */

    private String showapi_res_error;
    private String showapi_res_id;
    private int showapi_res_code;
    private ShowapiResBodyBean showapi_res_body;

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public String getShowapi_res_id() {
        return showapi_res_id;
    }

    public void setShowapi_res_id(String showapi_res_id) {
        this.showapi_res_id = showapi_res_id;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * ret_code : 0
         * data : [{"english":"I loved you. I leave you. I'll miss you.","chinese":"曾经爱过你。现在我走了。将来，我还会想念你。"}]
         * ret_message : Success
         */

        private int ret_code;
        private String ret_message;
        private List<DataBean> data;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public String getRet_message() {
            return ret_message;
        }

        public void setRet_message(String ret_message) {
            this.ret_message = ret_message;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * english : I loved you. I leave you. I'll miss you.
             * chinese : 曾经爱过你。现在我走了。将来，我还会想念你。
             */

            private String english;
            private String chinese;

            public String getEnglish() {
                return english;
            }

            public void setEnglish(String english) {
                this.english = english;
            }

            public String getChinese() {
                return chinese;
            }

            public void setChinese(String chinese) {
                this.chinese = chinese;
            }
        }
    }
}
