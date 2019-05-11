import {GetWebpackBaseConfigOptions} from "./webpack.base.config";
import {existsSync} from "fs";
import * as path from "path";
import * as ExtractTextWebpackPlugin from "extract-text-webpack-plugin";
import {lessModuleLoader} from "./cssModuleUtils";


/**
 * 获取主题配置
 * @param path    文件路径
 * @param isPackage  是否配置在package.json文件中
 */
function getTheme(path, isPackage) {

    let theme = {};
    if (isPackage) {
        //配置在package.json文件中
        const pkg = existsSync(path) ? require(path) : {};
        if (pkg.theme && typeof(pkg.theme) === 'string') {
            let cfgPath = pkg.theme;
            // relative path
            if (cfgPath.charAt(0) === '.') {
                cfgPath = path.resolve(global['args'].cwd, cfgPath);
            }
            theme = require(cfgPath);
        } else if (pkg.theme && typeof(pkg.theme) === 'object') {
            theme = pkg.theme;
        }
    } else {
        //使用单独的js 文件
        theme = require(path);
    }
    return theme;
}

function getLessLoader(options: GetWebpackBaseConfigOptions) {

    const isPackage = options.packagePath !== undefined && options.packagePath !== null;
    const theme = getTheme(isPackage ? options.packagePath : options.themePath, isPackage);

    return {
        test: /\.less$/,
        use: ExtractTextWebpackPlugin.extract({
            fallback: "style-loader",
            use: [
                lessModuleLoader,
                {
                    loader: "postcss-loader",
                    options: {
                        config: {
                            path: path.join(__dirname, './postcss.config.js')
                        },
                        ident:"css-loader"
                    }
                },
                {
                    // loader: require.resolve('less-loader'),
                    loader:'less-loader',
                    options: {
                        sourceMap: true,
                        javascriptEnabled: true,
                        modifyVars: theme,
                        ident:"css-loader"
                    }
                }
            ]
        })
    }
}

export default getLessLoader;
