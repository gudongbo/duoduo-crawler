package com.duoduo.crawler.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "page_info")
public class PageInfo {
    @Id
    @Column(name = "page_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pageId;

    @Column(name = "page_url")
    private String pageUrl;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "is_upload")
    private Integer isUpload;

    @Column(name = "is_zip")
    private Integer isZip;

    /**
     * @return page_id
     */
    public Long getPageId() {
        return pageId;
    }

    /**
     * @param pageId
     */
    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    /**
     * @return page_url
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * @param pageUrl
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return is_upload
     */
    public Integer getIsUpload() {
        return isUpload;
    }

    /**
     * @param isUpload
     */
    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    /**
     * @return is_zip
     */
    public Integer getIsZip() {
        return isZip;
    }

    /**
     * @param isZip
     */
    public void setIsZip(Integer isZip) {
        this.isZip = isZip;
    }
}