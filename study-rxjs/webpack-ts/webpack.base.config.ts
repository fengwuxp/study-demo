import * as webpack from "webpack";
import * as path from "path";
import * as ExtractTextWebpackPlugin from "extract-text-webpack-plugin";
// import CleanWebpackPlugin from "clean-webpack-plugin";
const CleanWebpackPlugin = require("clean-webpack-plugin");
// const WriteFilePlugin = require('write-file-webpack-plugin');
import {isExclude} from "./WebpackUtils";
import getLessLoader from "./getLessLoader";
import {scssModuleLoader, cssModuleLoader} from "./cssModuleUtils";


function getWebpackConfig() {
    // if (process.env._self !== "1") {
    //     return require("../../../webpack-config/WebpackConfig");
    // }
    return {
        DEPLOYMENT_DIRECTORY:"",
        PROJECT_DIR:""
    };
}

const {
    DEPLOYMENT_DIRECTORY,
    PROJECT_DIR
} = getWebpackConfig();


export interface GetWebpackBaseConfigOptions {

    /**
     * 样式json文件所在路径
     */
    themePath?: string

    /**
     * packageJson的文件路径
     */
    packagePath?: string;
}

/**
 * 获取打包配置
 * @param {GetWebpackBaseConfigOptions} options
 * @return {webpack.Configuration}
 */
export const getWebpackBaseConfig = function (options: GetWebpackBaseConfigOptions): webpack.Configuration {

    console.log("---------初始化打包配置--------", options);


    //默认打包目录
    const packPath = path.resolve("src", '../dist');

    const config: webpack.Configuration = {
        entry: {
            app: path.resolve('src', 'App'),
        },
        output: {
            filename: '[name]_[hash].js',
            chunkFilename: '[name]_[hash].js',
            path: packPath,
            publicPath: "/"
        },
        resolve: {
            extensions: [".ts", ".tsx", "d.ts", ".js", ".css", ".scss", ".less", ".png", "jpg", ".jpeg", ".gif"],
        },

        module: {
            rules: [
                {
                    test: /\.js[x]?$/,
                    // exclude: /(node_modules|bower_components)/,
                    exclude: isExclude,
                    use: [
                        {
                            loader: "babel-loader",
                            options: {
                                // presets: ['es2015', 'stage-2'],
                                presets: ["react", "env"],
                                compact: true
                            },
                        }
                    ]
                },
                {
                    test: /\.ts[x]?$/,
                    exclude: isExclude,
                    use: [
                        {
                            loader: "babel-loader",
                            options: {
                                cacheDirectory: true,
                                presets: ['es2015', 'stage-2']
                            }
                        },
                        {loader: "awesome-typescript-loader"}
                    ]
                },
                {
                    test: /\.css$/,
                    use: ExtractTextWebpackPlugin.extract({
                        fallback: "style-loader",
                        use: [
                            cssModuleLoader,
                            {
                                loader: "postcss-loader",
                                options: {
                                    config: {
                                        path: path.join(__dirname, './postcss.config.js')
                                    }
                                }
                            }
                        ]
                    }),

                },
                getLessLoader(options),

                {
                    test: /\.s[c|a]ss$/,
                    use: ExtractTextWebpackPlugin.extract({
                        fallback: "style-loader",
                        use: [
                            // require.resolve("style-loader"),
                            scssModuleLoader,
                            {
                                loader: "postcss-loader",
                                options: {
                                    config: {
                                        path: path.join(__dirname, './postcss.config.js')
                                    }
                                }
                            },
                            {loader: "sass-loader"}
                        ]
                    })
                },
                {
                    test: /\.(png|jpg|svg)/,
                    use: [
                        {
                            loader: "url-loader",
                            options: {
                                limit: 25000
                            }
                        }
                    ]
                },
                {
                    test: /\.(woff|woff2|svg|ttf|eot)$/,
                    use: [
                        {
                            loader: 'file-loader',
                            //项目设置打包到dist下的fonts文件夹下
                            options: {
                                name: 'fonts/[name].[hash:8].[ext]',
                                //20kb以下的直接打包到css文件中
                                limit: 1024 * 20,
                                //返回最终的资源相对路径
                                publicPath: function (url) {
                                    //使用全局变量来传递 资源根路径
                                    let uri = path.join(global['__RESOURCES_BASE_NAME__'], url).replace(/\\/g, '/');
                                    return uri;
                                }
                            },

                        }
                    ]
                }
            ]
        },
        // When importing a module whose path matches one of the following, just
        // assume a corresponding global variable exists and use that instead.
        // This is important because it allows us to avoid bundling all of our
        // dependencies, which allows browsers to cache those libraries between builds.
        externals: {
            "react": "React",
            "react-dom": "ReactDOM"
        },
        plugins: [
            new ExtractTextWebpackPlugin({
                filename: "[name].css",
                allChunks: true
            }),
            // new WriteFilePlugin({
            //     // test: /^((?!\.hot-update).)*$/,
            //     test: /\.jsp|\.tld|\.xml$/,
            // })
        ]
    };
    //是否打release包
    let release = process.env.RELEASE;
    if (release === "1") {
        //重写打包目录到部署目录
        config.output.path = DEPLOYMENT_DIRECTORY;
    }
    if (release != null) {
        config.plugins.push(
            //git https://github.com/johnagan/clean-webpack-plugin
            //先将部署目录清除
            new CleanWebpackPlugin([
                config.output.path
            ], {
                root: PROJECT_DIR,       　　　　　　   //根目录
                // verbose: true,        　　　　　　　  //开启在控制台输出信息
                // dry: false        　　　　　　　　　　//启用删除文件,
                allowExternal: true,                  //允许删除wbpack根目录之外的文件
                // beforeEmit: true                       //在将文件发送到输出目录之前执行清理
            }),
        );
    }


    return config;
};




