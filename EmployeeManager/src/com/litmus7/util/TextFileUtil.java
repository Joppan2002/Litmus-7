package com.litmus7.employeemanager.util;

import com.litmus7.employeemanager.dto.Employee;

import java.io.*;

public class TextFileUtil {
    public static String readFromTextFile(String inputFilePath) throws IOException {
        String line = "";
        String result = "";

        try {
            File file = new File(inputFilePath);
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                String[] arr = line.split("\\$");

                if (arr.length == 7) {
                    Employee emp = new Employee(
                        Integer.parseInt(arr[0].trim()),
                        arr[1].trim(),
                        arr[2].trim(),
                        arr[3].trim(),
                        arr[4].trim(),
                        arr[5].trim(),
                        arr[6].trim()
                    );

                    String validationResult = ValidationUtil.validateEmployee(emp);

                    if (validationResult.equals("valid")) {
                        line = line.replace('$', ',');
                    } else {
                        line = validationResult;
                    }
                } else {
                    line = "Incomplete Data";
                }

                result += line + "\n";
            }

            br.close();

        } catch (FileNotFoundException e) {
            Response<String, Boolean, String> ob = new Response<>("File Could not be found", false, "File Not Found");
            return ob.data;
        }

        Response<String, Boolean, String> ob = new Response<>(result, true, "Success");
        return ob.data;
    }
}
