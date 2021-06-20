package org.thenx.dao;

import org.thenx.entity.GeneratorTests;

/**
* 表generator_tests ->  的基本功能实现
*
* @author Wales-Yu
* @date 2021/06/20
*/
public interface GeneratorTestsMapper {
    /**
     *   deleteByPrimaryKey 实现
     * 
     * @param id
     * @return null int
     * @author Wales-Yu
     * @date 2021-06-20 15:39:22
     */
    int deleteByPrimaryKey(String id);

    /**
     *   insert 实现
     * 
     * @param record
     * @return null int
     * @author Wales-Yu
     * @date 2021-06-20 15:39:22
     */
    int insert(GeneratorTests record);

    /**
     *   insertSelective 实现
     * 
     * @param record
     * @return null int
     * @author Wales-Yu
     * @date 2021-06-20 15:39:22
     */
    int insertSelective(GeneratorTests record);

    /**
     *   selectByPrimaryKey 实现
     * 
     * @param id
     * @return null org.thenx.entity.GeneratorTests
     * @author Wales-Yu
     * @date 2021-06-20 15:39:22
     */
    GeneratorTests selectByPrimaryKey(String id);

    /**
     *   updateByPrimaryKeySelective 实现
     * 
     * @param record
     * @return null int
     * @author Wales-Yu
     * @date 2021-06-20 15:39:22
     */
    int updateByPrimaryKeySelective(GeneratorTests record);

    /**
     *   updateByPrimaryKey 实现
     * 
     * @param record
     * @return null int
     * @author Wales-Yu
     * @date 2021-06-20 15:39:22
     */
    int updateByPrimaryKey(GeneratorTests record);
}