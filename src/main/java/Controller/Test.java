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
public class Test {

    public static void main(String[] args) {
        String cmd="01 03 00 16 00 01 65 CE";
        // 实例化串口操作类对象
        SerialPortUtils serialPort = new SerialPortUtils();
        /** 串口号 */
        String serialNumber="COM3";
        /** 波特率 */
        int baudRate=9600;
        /** 校验位 */
        int checkoutBit=0;
        /** 数据位 */
        int dataBit=8;
        /** 停止位 */
        int stopBit=1;
        ParamConfig paramConfig = new ParamConfig(serialNumber, baudRate,checkoutBit ,dataBit ,stopBit );
        // 初始化设置,打开串口，开始监听读取串口数据
        serialPort.init(paramConfig);
        // 调用串口操作类的sendComm方法发送数据到串口
        serialPort.sendComm(cmd);
        String dataHex = serialPort.getDataHex();
        System.out.println(dataHex);
    }

}
