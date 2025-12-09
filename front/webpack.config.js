const { join } = require('path');
module.exports = {
  module: {
    rules: [
      {
        test: /\.(ts|js)$/,
        include: join(__dirname, 'src'),
        loader: 'coverage-istanbul-loader',
        options: { esModules: true },
        enforce: 'post',
        exclude: [
          /\.(e2e|spec)\.ts$/,
          /node_modules/
        ]
      }
    ]
  }
};
