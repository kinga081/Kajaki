package com.example.kajax;

class Lokalizacje {
/*
    "localid": "-LTdVr2MaVHfqOlKxkzK-",
            "name": "Spływy Kajakowe po rzekach Roztocza - \"U Rudego\"",
            "adress": "Obrocz 116, 22-470 Obrocz",
            "h_open": "cały tydzień 09:00–18:00",
            "tel": 885604286,
            "web": "splywy-roztocze.pl",
            "local": "Obrocz",
            "Latitude": 50.6027829,
            "longitude": 23.0264988

 */
    String localid;
    String name;
    String adress;
    String h_open;
    String tel;
    String web;
    String local;
    float Latitude;
    float longitude;

    Lokalizacje(){}

    public String getLocalid() {
        return localid;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public String getH_open() {
        return h_open;
    }

    public String getTel() {
        return tel;
    }

    public String getWeb() {
        return web;
    }

    public String getLocal() {
        return local;
    }

    public float getLatitude() {
        return Latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
