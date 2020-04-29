package com.wisewin.api.util;

/**
 * Created by 王彬 on 2019/5/22.
 */
public class AlipayConfig {
    //商户appid
    public static final String APP_ID = "2019091267271292";
    //支付宝网关
    public static final String URL = "https://openapi.alipay.com/gateway.do";
    //开发者应用私钥
    public static final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLbN04Jq6D415F3yr+C3JSOi6gaS1PBebHCWRynTiPOy/dAc0RYA6sCA87F2h4iaGXz5kYcBI7ByjtBxyML5ZyYstK7bEzt8VWdsFuu/YwKGdVSRf4MgX6O25LqrprXQ7j11JQSaaeCjgrkSkb0Pl2dnOQvYVaUEjkFNhsoGgDGtJp9uaJHNcrdhx2IjMOtmJpVdFu4vSYAu5BeskYcmzwhLq1VMS6m6pl/K7jnLmJqEiO1HHLsDLu1s8xUplP+PesUvO+fvndg0TqqV4a1uhD0O6FFSyfBkT3k686bMzQYg1yPAUrZDzppRTwvBgW+T19HR8J4hz4AHUPomY+HTjlAgMBAAECggEAeNaBZIkh92y36BbjTArfqiSDBMQjYLncqeSaCjv5KSpC8Wj+MnDuRms1yCEhila+lrR2G5gGNOKftc7UDyuvWLQHPYmEay56+dUzEKYXWOWrqV8QRywTwiyAC1SM/UBKB243CR7MKWzyAdBtsI4Np35B6tBXI08x3Xuzl3Hus8DeMiMMSaNIo4WiZRkNc/RGbY5mz5PsjhoyDGP3FfUyQmnDLB9kZ83Kf8GUNhhcpAa0DjHV4tKe7hBYwDXjZD59qb0mcGmopLUA1BBiXjuT1SAxJObh43z4GrhMTTQdoee9FcA7udlNV8d4f5GlEhgdbl8xK2kZtwolv4TvpedIAQKBgQDjjaZngh8aQqSPzbeYEAt1OXNN7tKzeHC8Pql8HljOGzP+puxC5PmhsCnq/Bgl46hx7mWOEYelrtgTjjp65cZdkEDmFoJ7FTYFiwLsqp5xeOvg9G58vZu3Wtvm0xCUh3TwXrcY0nXxrFEgJS8zMotc4oFhdEEml4UV/PfcUlHh9QKBgQCc2t3YGfVvSRjFJVYKtfhmndW65FLnZ/EYTyhlqriFDgOMgevSbI4MZigOgaNbRck2cT6zLKKO+kPuL4D0U9WRa0YLuFr+gFgKHRWvGda99u/8YRe6ppPSTQgVrwnRf0ZkxH7uZKmcN5v+2/JDfL5t6mFU1D+WUnmCzBeBN/x1MQKBgEPerOI0ikhP1jtZ9UdyiahWEVEOSWPkKWPMIMr3Vi9rJ6/tY4x0NQyHuYjv1df/Ne99VmbwTZAaPTJf+otLW0Xuz5yQ3CwP4IP4uU+TeByKH3iCDy4/c2FVxT7sdwaF/FfxOBU8OJdzeOLolFJl4fE7UbGadhAnozcys07QcsG9AoGAAeD5QUpmTzPv8QSWeEBfvlLrqXZsq8rp72GbWURnO2jEkB1KRtX4CZv7pNjWrRQz6cwHdTv2ooHK6IJ3J/YNG9XOG/vUUzIUW+KnTNahZNsdWseK8jhmkuFLVH8pPfIpVLGp148rHOzpmG7oAvjuiwCAs/Rq4pEQVYm+SlTyWDECgYAz8fO5T8esmuR8Qi3FrE7oNA3PTRvyB9kD2Kby426Lx+bcVkdyPU87MoclZOM0UDyCRqkg028sbKRxX50Hkz7rnteK/Og5bYzaY6k/oQ874CE0U+K7HEu+IMjDdEVHziI7LgGrgrt6Vjdy2AAYr1a5k85pE21mhFBwVOlLZ/9j8Q==";
    //传输格式
    public static final String FORMAT = "JSON";
    //编码
    public static final String CHARSET = "utf-8";
    //支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoz2nNOxJlDTZUuO6PRtncHfUPlTD8JnWvzigR5gb8eAbqVSSOhXsDtmlTGPSSpIuzPqaVStrZKH7wFIhOWIn1z+tbl1du3eNsKJODwhRvzKQmf0c+DpxtdV0nQ3EAn4OL4kJPZ0Cs7DFTZ2vbdbPZqvDig634EwAvGYyE1uifPF0a+88r/+VFifsRUaivhsuq1AZxaQwMTGZgiUePLQkEdhCF52aC2kQBK6JD25wLQLfwDNLiVNSeXYMMmBbySiqB/vL2gSfrAnb+p2zopPI1nimMiZ4Ovzh29PSW+Cdugm3hvSCDecVCABO/wSmGTvDE9GMF++hRri6Y4xeGDgnswIDAQAB";
    //商户生成签名字符串所使用的签名算法类型
    public static final String SIGN_TYPE = "RSA2";
    //服务器异步通知页面路径
    public static final String fy_url = "http://39.106.54.2:8080/pay/notify";
    //页面跳转同步通知页面路径
    public static final String return_url = "http://39.106.54.2:8080/wbalipay/return_url";

}
