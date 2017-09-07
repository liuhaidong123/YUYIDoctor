package com.doctor.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/4/7.
 */
//我的帖子
public class Bean_MyPostData {

    /**
     * total : 2
     * rows : [{"commentList":[],"createTimeString":"2017-09-05 15:45:01","updateTimeString":"","isLike":false,"avatar":"/static/image/avatar.jpeg","title":"外分泌胰腺糖尿病的临床特征","shareNum":null,"content":"近期，一项发表在杂志Diabetes Care上的研究描述了胰腺疾病后糖尿病的发病率，评估了这些患者如何被临床医生分类，并比较了1型和2型糖尿病的临床特征。\n\n        此项研究自2005年1月1日至2016年3月31日期间，检索了英国初级保健记录(n = 2,360,631)中成人糖尿病。研究者们检查了在糖尿病诊断之前，有和无胰腺疾病患者的人口统计学，糖尿病分类，血糖控制和胰岛素使用(分类为急性胰腺炎或慢性胰腺疾病)。使用回归分析评估血糖控制不良(HbA1c≥7%[53 mmol / mol])和胰岛素需求的潜在危险因素。\n\n        最终，此项研究确定了31,789例成人新发病糖尿病。胰腺疾病后的发生糖尿病者(每10万人年为2.59 [95%CI 2.38-2.81])比1型糖尿病(1.64 [1.47-1.82]; P <0.001)更为常见。胰腺疾病后的559例糖尿病大多被分类为2型糖尿病(87.8%)，并且为外分泌胰腺糖尿病者十分罕见(2.7%)。\n\n        胰腺疾病后诊断为糖尿病的中位年龄为59岁，体重指数为29.2kg/m2。与2型糖尿病相比，胰腺疾病后的糖尿病与血糖控制不佳(调整后的OR值，1.7 [1.3-2.2]; P <0.001)。5年内2型糖尿病患者胰岛素使用量为4.1%(3.8-4.4)，急性胰腺炎后糖尿病者为20.9%(14.6-28.9)，慢性胰腺疾病后糖尿病者为45.8%(34.2-57.9)。\n\n        此项研究表明：外分泌胰腺的糖尿病经常被认为2型糖尿病，但是其血糖控制较差，对胰岛素的需求显着增加。","picture":"/static/image/201795/c912383f5c52415798279e0410120e8e.jpg;/static/image/201795/6378968ef0264bf58383d768c836a48a.jpg;","likeNum":null,"commentNum":null,"trueName":"张医生","physicianId":35,"id":123},{"commentList":[],"createTimeString":"2017-09-05 15:41:45","updateTimeString":"","isLike":true,"avatar":"/static/image/avatar.jpeg","title":"血糖过高 注意6征兆","shareNum":null,"content":"   美国逾1500万男性有糖尿病，但是四分之一的人不知道。血糖过高造成的症状逐渐发生，并不明显，所以你可能不知道自己有病;但如果出现以下的意外征兆，很可能就是血糖过高的象征。\n\n        1. 频尿：着有「终结糖尿病」 (The End of Diabetes)一书的佛尔曼(Joel Fuhrman)医生表示，上厕所次数增加，是血糖太高的特征，因为肾脏试图排掉血里过多的葡萄糖，导致你老想小便。\n\n        2. 严重口渴：佛尔曼说，排尿增加意味身体排出比平常多的水分，使你陷入脱水的危险，让你感到口渴和嘴巴干燥，即使你喝的水一样多。\n\n        3. 疲倦：这是脱水的典型副作用。频尿又口渴，会让你觉得精疲力尽。即使你认为睡眠时间一样多，仍使你感到疲倦，因为你可能晚上起床上好几次厕所，中断睡眠。\n\n        4. 视线模糊：位于眼睛中间的黄斑部，是视网膜的视觉中枢，负责清晰的中心视觉。佛尔曼说，血糖过高，可渗进黄斑部造成肿胀，改变黄斑部的形状，无法聚焦，以致视线模糊。\n\n        5. 牙床出血：血糖高使口腔变成细菌更易滋生的地方，降低口腔抗菌的天然能力。爱吃糖的细5菌可使牙床红肿、疼痛，刷牙或用牙线时较可能出血。\n\n        6. 皮肤有奇怪的斑块：血糖高伤害血管，包括皮肤下的血管，形成发亮或会脱皮的红棕色斑块，特别在小腿，斑块会痒甚至痛。佛尔曼说，暗色平滑的斑块也可能出现在腋下、腹股沟和颈部，这是因为太多血糖，使皮肤细胞生长更快且含更多色素，这种斑块会发痒或散发异味。","picture":"/static/image/201795/44d0cdd6f2114f43b678fda2f202634e.jpg;","likeNum":1,"commentNum":null,"trueName":"张医生","physicianId":35,"id":122}]
     * colmodel : []
     */

    private int total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public List<?> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<?> colmodel) {
        this.colmodel = colmodel;
    }

    public static class RowsBean {
        /**
         * commentList : []
         * createTimeString : 2017-09-05 15:45:01
         * updateTimeString :
         * isLike : false
         * avatar : /static/image/avatar.jpeg
         * title : 外分泌胰腺糖尿病的临床特征
         * shareNum : null
         * content : 近期，一项发表在杂志Diabetes Care上的研究描述了胰腺疾病后糖尿病的发病率，评估了这些患者如何被临床医生分类，并比较了1型和2型糖尿病的临床特征。

         此项研究自2005年1月1日至2016年3月31日期间，检索了英国初级保健记录(n = 2,360,631)中成人糖尿病。研究者们检查了在糖尿病诊断之前，有和无胰腺疾病患者的人口统计学，糖尿病分类，血糖控制和胰岛素使用(分类为急性胰腺炎或慢性胰腺疾病)。使用回归分析评估血糖控制不良(HbA1c≥7%[53 mmol / mol])和胰岛素需求的潜在危险因素。

         最终，此项研究确定了31,789例成人新发病糖尿病。胰腺疾病后的发生糖尿病者(每10万人年为2.59 [95%CI 2.38-2.81])比1型糖尿病(1.64 [1.47-1.82]; P <0.001)更为常见。胰腺疾病后的559例糖尿病大多被分类为2型糖尿病(87.8%)，并且为外分泌胰腺糖尿病者十分罕见(2.7%)。

         胰腺疾病后诊断为糖尿病的中位年龄为59岁，体重指数为29.2kg/m2。与2型糖尿病相比，胰腺疾病后的糖尿病与血糖控制不佳(调整后的OR值，1.7 [1.3-2.2]; P <0.001)。5年内2型糖尿病患者胰岛素使用量为4.1%(3.8-4.4)，急性胰腺炎后糖尿病者为20.9%(14.6-28.9)，慢性胰腺疾病后糖尿病者为45.8%(34.2-57.9)。

         此项研究表明：外分泌胰腺的糖尿病经常被认为2型糖尿病，但是其血糖控制较差，对胰岛素的需求显着增加。
         * picture : /static/image/201795/c912383f5c52415798279e0410120e8e.jpg;/static/image/201795/6378968ef0264bf58383d768c836a48a.jpg;
         * likeNum : null
         * commentNum : null
         * trueName : 张医生
         * physicianId : 35
         * id : 123
         */

        private String createTimeString;
        private String updateTimeString;
        private boolean isLike;
        private String avatar;
        private String title;
        private Object shareNum;
        private String content;
        private String picture;
        private Long likeNum;
        private Object commentNum;
        private String trueName;
        private int physicianId;
        private Long id;
        private List<?> commentList;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public boolean isIsLike() {
            return isLike;
        }

        public void setIsLike(boolean isLike) {
            this.isLike = isLike;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getShareNum() {
            return shareNum;
        }

        public void setShareNum(Object shareNum) {
            this.shareNum = shareNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Long getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(Long likeNum) {
            this.likeNum = likeNum;
        }

        public Object getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(Object commentNum) {
            this.commentNum = commentNum;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(int physicianId) {
            this.physicianId = physicianId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<?> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<?> commentList) {
            this.commentList = commentList;
        }
    }
}
