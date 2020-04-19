package com.example.networktest.network.kryonet;

public interface KryoNetComponent {
    /**
     * Register class for serialization
     *
     * @param c
     */
    void registerClass(Class c);

}
