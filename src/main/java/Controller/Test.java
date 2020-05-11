package Controller;

import lombok.extern.slf4j.Slf4j;
import serialcomm.ParamConfig;
import serialcomm.SerialPortUtils;

/**
 * @program: serialcomm
 * @description
 * @author: zgh
 * @create: 2020-05-11 10:34
 **/
@Slf4j
public class Test {

    public static void main(String[] args) {
        String cmd="01 03 00 16 00 01 65 CE";
        // 实例化串口操作类对象
        SerialPortUtils serialPort = new SerialPortUtils();
        String serialNumber="";
        ParamConfig paramConfig = new ParamConfig("COM3", 9600, 0, 8, 1);

    }

}
