package com.bacchuserpshop.common.util;

public class DialogUtilsInterface {

    DialogReturn dialogReturn;

    public interface DialogReturn {

        void onDialogCompleted(boolean answer);
    }

    public void setListener(DialogReturn dialogReturn) {
        this.dialogReturn = dialogReturn;
    }

    public DialogReturn getListener() {
        return dialogReturn;

    }
}