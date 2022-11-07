package com.zhuang.util.spring;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionUtils {

    public static void doAfterCompletion(MyTransactionSynchronization myTransactionSynchronization) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(myTransactionSynchronization);
        }
    }


    public static class MyTransactionSynchronization implements TransactionSynchronization {
        private Runnable runnable;

        public MyTransactionSynchronization(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void afterCompletion(int status) {
            if (TransactionSynchronization.STATUS_COMMITTED == status) {
                this.runnable.run();
            }
        }
    }
}
