package serialcomm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParamConfig {
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
}
