module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: ["plugin:vue/essential", "@vue/prettier"],
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
    "vue/no-use-v-if-with-v-for": "off",
    'prettier/prettier': 0,
    'vue/no-parsing-error': ["error", {
      "invalid-first-character-of-tag-name": false,
    }],
  },
  parserOptions: {
    parser: "babel-eslint"
  }
};
