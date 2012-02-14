package psimulator.AbstractNetwork;

import javax.xml.bind.annotation.XmlEnum;

/**
 *
 * @author Martin
 */
@XmlEnum
public enum HwTypeEnum {
    LINUX_ROUTER,
    CISCO_ROUTER,
    LINUX_SWITCH,
    CISCO_SWITCH,
    END_DEVICE_NOTEBOOK,
    END_DEVICE_PC,
    END_DEVICE_WORKSTATION,
    REAL_PC,
    CABLE_ETHERNET,
    CABLE_OPTIC,
    BUNDLE_OF_CABLES;
}
