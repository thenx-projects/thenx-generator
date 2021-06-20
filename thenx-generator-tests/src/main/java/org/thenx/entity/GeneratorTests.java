package org.thenx.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author Wales-Yu
* <p>
* 实体
* @date Sun Jun 20 15:39:22 CST 2021
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorTests implements java.io.Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 员工编号
     */
    private Integer pId;

    /**
     * 所属部门
     */
    private Integer departmentId;

    /**
     * 职称ID
     */
    private Integer jobLevelId;

    /**
     * 职位ID
     */
    private Integer posid;

    /**
     * 聘用形式
     */
    private String engageForm;

    /**
     * 最高学历
     */
    private String tiptopDegree;

    /**
     * 所属专业
     */
    private String specialty;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 入职日期
     */
    private Date beginDate;

    /**
     * 在职状态
     */
    private String workState;

    /**
     * 工号
     */
    private String workId;

    /**
     * 合同期限
     */
    private Double contractTerm;

    /**
     * 转正日期
     */
    private Date conversionTime;

    /**
     * 离职日期
     */
    private Date notworkDate;

    /**
     * 合同起始日期
     */
    private Date beginContract;

    /**
     * 合同终止日期
     */
    private Date endContract;

    /**
     * 工龄
     */
    private Integer workAge;
}