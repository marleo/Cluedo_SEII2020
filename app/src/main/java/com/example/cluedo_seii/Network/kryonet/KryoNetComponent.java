package com.example.cluedo_seii.Network.kryonet;

public interface KryoNetComponent {
    /**
     * Register class for serialization
     *
     * @param c
     */
    void registerClass(Class c);

}
