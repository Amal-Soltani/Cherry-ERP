const childProcess = require('child_process');
const { writeFileSync } = require('fs-extra');
const CryptoJS = require('crypto-js');
const fs = require('fs');


const SHA = childProcess.execSync('git rev-parse HEAD').toString().trim();
const shortSHA = childProcess.execSync('git rev-parse --short HEAD').toString().trim();
const branch = childProcess.execSync('git rev-parse --abbrev-ref HEAD').toString().trim();
const authorName = childProcess.execSync("git log -1 --pretty=format:'%an'").toString().trim();
const commitTime = childProcess.execSync("git log -1 --pretty=format:'%ad'").toString().trim();
const commitMsg = childProcess.execSync('git log -1 --pretty=%B').toString().trim();
const totalCommitCount = childProcess.execSync('git rev-list --count HEAD').toString().trim();

const versionInfo = {
  SHA: SHA,
  shortSHA: shortSHA,
  branch: branch,
  authorName: authorName,
  commitTime: commitTime,
  commitMsg: commitMsg,
  totalCommitCount: totalCommitCount
}

const versionInfoJson = JSON.stringify(versionInfo, null, 2);

writeFileSync('src/assets/version/git.version.json', versionInfoJson);

const args = process.argv.slice(2);
let configFileName = 'src/assets/config/config.json';
if (args.length > 0) {
  configFileName = 'src/assets/config/config.' + args[0] + '.json'
}
const configs = JSON.parse(fs.readFileSync(configFileName));
console.log('Environment: ' + configs.environment);
const encriptedConfigs = encodeURIComponent(CryptoJS.AES.encrypt(JSON.stringify(configs), '4f0a4183-574a-4540-8055-9a7a7490de70').toString());
writeFileSync('src/assets/config/config', encriptedConfigs);
