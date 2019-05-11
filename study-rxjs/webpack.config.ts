import  baseConfig from "./webpack-ts/webpack.config.template";
// import * as HtmlWebPackPlugin from "html-webpack-plugin";
const HtmlWebPackPlugin=require("html-webpack-plugin");
import * as webpack from "webpack";

const config: webpack.Configuration ={
    ...baseConfig
};



config.plugins.push(
    new HtmlWebPackPlugin({
        template: './src/index.html',
        filename: "index.html",
        title: "antd mobile template",
        chunks: ['app'],
        inject: true,
    })
);


export default config;
