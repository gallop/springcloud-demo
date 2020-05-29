package com.gallop.common.serialization;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * author gallop
 * date 2020-05-26 9:35
 * Description:商户运营时间段配置类
 * Modified By:
 */

public class OperateTimeBetween implements Serializable {
    private static final long serialVersionUID = 8802397870355688593L;

    private boolean isOperate; //运营状态开关
    private LocalTime start; //运营开始时间
    private LocalTime end; // 运营结束时间

    public OperateTimeBetween() {
    }

    public OperateTimeBetween(boolean isOperate, LocalTime start, LocalTime end) {
        this.isOperate = isOperate;
        this.start = start;
        this.end = end;
    }

    public boolean isOperate() {
        return isOperate;
    }

    public void setOperate(boolean operate) {
        isOperate = operate;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "OperateTimeBetween{" +
                "isOperate=" + isOperate +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
