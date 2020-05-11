package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @program: serialcomm
 * @description
 * @author: zgh
 * @create: 2020-05-11 10:43
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerialPortDTO {
    /** 串口号 */
    private String serialNumber;
    /** 波特率 */
    private int baudRate;
    /** 校验位 */
    private int checkoutBit;
    /** 数据位 */
    private int dataBit;
    /** 停止位 */
    private int stopBit;

    public SerialPortDTO(String serialNumber, int baudRate, int checkoutBit, int dataBit, int stopBit) {
        this.serialNumber = serialNumber;
        this.baudRate = baudRate;
        this.checkoutBit = checkoutBit;
        this.dataBit = dataBit;
        this.stopBit = stopBit;
    }
}
