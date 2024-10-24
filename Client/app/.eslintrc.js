module.exports = {
    root: true, 
    env: {
      node: true, 
      browser: true, 
    },
    extends: [
      'plugin:vue/essential',
      'eslint:recommended', 
    ],
    parserOptions: {
      parser: 'babel-eslint',
    },
    rules: {
        //add regras adicionais se precisar dps
      'no-unused-vars': 'warn',
    },
  };
  