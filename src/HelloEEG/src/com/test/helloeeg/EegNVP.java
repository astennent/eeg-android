package com.test.helloeeg;


import org.apache.http.NameValuePair;

public class EegNVP implements NameValuePair {

        private String name;
        private String value;

        public EegNVP(String name, String value) {
                this.name = name;
                this.value = value;
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public String getValue() {
                return value;
        }

}