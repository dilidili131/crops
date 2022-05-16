module.exports = {
    devServer: {
        open: true,
        host: 'localhost',
        port: 8080,
        https: false,
        //以上的ip和端口是我们本机的;下面为需要跨域的
        proxy: { //配置跨域
            '/api': {
                target: 'http://8.142.116.170:9527/api', //这里后台的地址模拟的;应该填写你们真实的后台接口
                ws: true,
                changOrigin: true, //允许跨域
                pathRewrite: {
                    '^/api': '' //请求的时候使用这个api就可以
                }
            },
            '/appi': {
                target: 'http://localhost:8003/api', //这里后台的地址模拟的;应该填写你们真实的后台接口
                ws: true,
                changOrigin: true, //允许跨域
                pathRewrite: {
                    '^/appi': '' //请求的时候使用这个api就可以
                }
            },
            // '/img': {
            //     target: 'http://39.99.158.5:5001', //这里后台的地址模拟的;应该填写你们真实的后台接口
            //     ws: true,
            //     changOrigin: true, //允许跨域
            //     pathRewrite: {
            //         '^/img': '' //请求的时候使用这个api就可以
            //     }
            // }

        }
    }
}