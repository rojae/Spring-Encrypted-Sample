package com.encrypt.demo.mapper;

import com.encrypt.demo.domain.TbRsaDemo;

import java.util.List;

public interface TbRsaDemoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_rsa_demo
     *
     * @mbg.generated Thu Jan 13 17:43:28 KST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_rsa_demo
     *
     * @mbg.generated Thu Jan 13 17:43:28 KST 2022
     */
    int insert(TbRsaDemo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_rsa_demo
     *
     * @mbg.generated Thu Jan 13 17:43:28 KST 2022
     */
    TbRsaDemo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_rsa_demo
     *
     * @mbg.generated Thu Jan 13 17:43:28 KST 2022
     */
    List<TbRsaDemo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_rsa_demo
     *
     * @mbg.generated Thu Jan 13 17:43:28 KST 2022
     */
    int updateByPrimaryKey(TbRsaDemo record);
}