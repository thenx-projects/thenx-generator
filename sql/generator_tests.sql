/*
 * Copyright [2021-2021] [Thenx Projects]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

create table generator_tests
(
    id              varchar(100)                                          not null comment '主键ID'
        primary key,
    p_id            int                                                   not null comment '员工编号',
    department_id   int                                                   null comment '所属部门',
    job_level_id    int                                                   null comment '职称ID',
    posId           int                                                   null comment '职位ID',
    engage_form     varchar(8)                                            null comment '聘用形式',
    tiptop_degree   enum ('博士', '硕士', '本科', '大专', '高中', '初中', '小学', '其他') null comment '最高学历',
    specialty       varchar(32)                                           null comment '所属专业',
    school          varchar(32)                                           null comment '毕业院校',
    begin_date      date                                                  null comment '入职日期',
    work_state      enum ('在职', '离职') default '在职'                        null comment '在职状态',
    work_id         char(8)                                               null comment '工号',
    contract_term   double                                                null comment '合同期限',
    conversion_time date                                                  null comment '转正日期',
    notWork_date    date                                                  null comment '离职日期',
    begin_contract  date                                                  null comment '合同起始日期',
    end_contract    date                                                  null comment '合同终止日期',
    work_age        int                                                   null comment '工龄'
)
    charset = utf8;

