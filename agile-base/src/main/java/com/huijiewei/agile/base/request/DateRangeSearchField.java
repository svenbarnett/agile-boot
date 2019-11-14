package com.huijiewei.agile.base.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DateRangeSearchField extends BaseSearchField<DateRangeSearchField> {
    private String labelStart;
    private String labelEnd;
    private String shortcuts;

    public DateRangeSearchField() {
        this.setType("dateRange");
    }

    public DateRangeSearchField labelStart(String labelStart) {
        this.labelStart = labelStart;

        return this;
    }

    public DateRangeSearchField labelEnd(String labelEnd) {
        this.labelEnd = labelEnd;

        return this;
    }

    @Override
    protected DateRangeSearchField self() {
        return this;
    }
}
