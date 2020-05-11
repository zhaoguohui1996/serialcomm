package demo;

import serialcomm.CrcUtil;
import serialcomm.HexUtils;
import serialcomm.ParamConfig;
import serialcomm.SerialPortUtils;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @program: serialcomm
 * @description
 * @author: zgh
 * @create: 2020-05-11 10:34
 **/
public class WindSpeedTest {

    public static void main(String[] args) throws InterruptedException {
        /** 串口号 */
        String serialNumber = "COM3";
        /** 波特率 */
        int baudRate = 9600;
        /** 校验位 */
        int checkoutBit = 0;
        /** 数据位 */
        int dataBit = 8;
        /** 停止位 */
        int stopBit = 1;

        String cmd = "01 03 00 16 00 01";
        byte[] bytes = HexUtils.hexStringToByte(cmd);
        int crc16 = CrcUtil.calcCrc16(bytes);
        String s = HexUtils.changeHighAndLow(Integer.toHexString(crc16));
        System.out.println(s);
        cmd += s;

        String replace = cmd.replace(" ", "");
        System.out.println("发送数据:" + replace);
        // 实例化串口操作类对象
        SerialPortUtils serialPort = new SerialPortUtils();
        //设置参数
        ParamConfig paramConfig = new ParamConfig(serialNumber, baudRate, checkoutBit, dataBit, stopBit);
        // 初始化设置,打开串口，开始监听读取串口数据
        serialPort.init(paramConfig);
        while (true) {
            // 调用串口操作类的sendComm方法发送数据到串口
            serialPort.sendComm(replace);
            String dataHex = serialPort.getDataHex();
            while (dataHex == null) {
                Thread.sleep(2000);
                dataHex = serialPort.getDataHex();
            }

            System.out.println("返回结果:" + dataHex);
            String rcrc = dataHex.substring(dataHex.length() - 4);
            String data = dataHex.substring(0, dataHex.length() - 4);
            byte[] rbytes = HexUtils.hexStringToByte(data);
            int rcrc16 = CrcUtil.calcCrc16(rbytes);
            String s1 = HexUtils.changeHighAndLow(Integer.toHexString(rcrc16));
            System.out.println(s1);
            if (s1.replace(" ", "").equalsIgnoreCase(rcrc)) {
                System.out.println("校验成功");
                String validWords = dataHex.substring(4, 6);
                int validNumber = HexUtils.hexStringToByte(validWords)[0];
                System.out.println("有效数字为:" + validNumber);
                String dataLength = dataHex.substring(6, 6 + validNumber * 2);
                System.out.println(dataLength);
                double windSpeed = Integer.parseInt(dataLength, 16) * 0.1;
                System.out.println("风速为:" + String.format("%.2f", windSpeed) + "m/s");
            }
            Thread.sleep(5000);
        }
    }


}
