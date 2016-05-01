package com.shamir;

import java.math.BigInteger;

/**
 * Created by dhavallad on 4/28/16.
 */

public class SecretShare {
        public SecretShare(final int num, final BigInteger share) {
            this.num = num;
            this.share = share;
        }

        public int getNum() {
            return num;
        }

        public BigInteger getShare() {
            return share;
        }

        @Override
        public String toString() {
            return "Secret Share: Number#" + num + " -> ShareValue=" + share;
        }

        private final int num;
        private final BigInteger share;
}
