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

package org.thenx.boot;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author May
 * <p>
 * 单独启动 Generator
 */
public class RunWithGenerator {

    public static void main(String[] args) throws Exception{

        Scanner scanner = new Scanner(System.in);
        System.out.print("确认不是重复执行（重新执行前记得clean一下maven） -> 输入 Y 立刻执行；输入 N 取消执行：");
        if (scanner.hasNextLine()) {
            String str2 = scanner.nextLine();
            if ("Y".equals(str2)) {
                RunWithGenerator app = new RunWithGenerator();
                System.out.println(app.getClass().getResource("/").getPath());
                app.generator();
                System.out.println("===> " + System.getProperty("user.dir"));
            } else {
                System.out.println("已取消执行 !");
            }
        }
        scanner.close();
    }

    public void generator() throws Exception{

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resourceAsStream);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        for(String warning:warnings){
            System.out.println(warning);
        }
    }
}
