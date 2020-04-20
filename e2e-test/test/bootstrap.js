const puppeteer = require('puppeteer');
const { expect } = require('chai');
const _ = require('lodash');
const globalVariables = _.pick(global, ['browser', 'expect']);


const options = {
  headless: false,
  timeout: 10000
};


before (async function () {
  global.expect = expect;
  global.browser = await puppeteer.launch(options);
});


after (function () {
  browser.close();

  global.browser = globalVariables.browser;
  global.expect = globalVariables.expect;
});