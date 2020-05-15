package demo;

import lombok.Data;
import serialcomm.CrcUtil;
import serialcomm.HexUtils;
import serialcomm.ParamConfig;
import serialcomm.SerialPortUtils;

/**
 * @program: serialcomm
 * @description
 * @author: zgh
 * @create: 2020-05-11 10:34
 **/
@Data
public class NoiseTest {
    ////////////////默认值////////////////////
    /**
     * 串口号
     */
    private String serialNumber = "COM3";
    /**
     * 波特率
     */
    private int baudRate = 9600;
    /**
     * 校验位
     */
    private int checkoutBit = 0;
    /**
     * 数据位
     */
    private int dataBit = 8;
    /**
     * 停止位
     */
    private int stopBit = 1;

    private void data(String deviceAddress) throws InterruptedException {
        /** 查1# 设备 1个数据*/
        /** 设备地址 */
        if (deviceAddress == null || deviceAddress.isEmpty()) {
            deviceAddress = "01";
        }
        /** 功能 */
        String function = "03";
        /** 寄存器地址 */
        String registerAddress = "0000";
        String cmd = deviceAddress + function + registerAddress + "0001";

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
                String dataString = dataHex.substring(6, 6 + validNumber * 2);
                System.out.println(dataString);
                double dataDouble = Integer.parseInt(dataString, 16);
                System.out.println(dataDouble/10+"DB");
            }
            Thread.sleep(5000);
        }
    }

    private String getDeviceAddress() throws InterruptedException {
        /** 若不知设备地址 FA=250 */
        String deviceAddress = "FA";
        /** 功能 */
        String function = "03";
        /** 寄存器地址 */
        String registerAddress = "0064";
        String cmd = deviceAddress + function + registerAddress + "0002";
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
            deviceAddress = dataHex.substring(0, 2);
            System.out.println("设备地址:" + deviceAddress);
            int deviceNo = Integer.parseInt(dataHex.substring(6, 10), 16);
            System.out.println("设备编号:" + deviceNo);
            System.out.println("设备状态量:" + dataHex.substring(10, 14));
        }
        serialPort.closeSerialPort();
        return deviceAddress;
    }

    private void setDeviceAddress(String originDeviceAddress,String destinationDeviceAddress) throws InterruptedException {
        /** 功能 */
        String function = "06";
        /** 寄存器地址 */
        String registerAddress = "0066";
        String cmd = originDeviceAddress + function + registerAddress + destinationDeviceAddress;
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
            System.out.println("设备地址:" + dataHex.substring(0, 2));
            if (dataHex.substring(2, 10).equalsIgnoreCase(replace.substring(2, 10))) {
                System.out.println("修改成功!");
            }
        }
        serialPort.closeSerialPort();
    }

    public static void main(String[] args) {
        NoiseTest test = new NoiseTest();
        test.setSerialNumber("COM3");
        try {
            String deviceAddress = test.getDeviceAddress();
//            test.setDeviceAddress(deviceAddress,"0002");
//            deviceAddress = test.getDeviceAddress();
            test.data(deviceAddress);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
