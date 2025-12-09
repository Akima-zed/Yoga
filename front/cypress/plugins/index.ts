

import codeCoverage from '@cypress/code-coverage/task';

export default function (on: Cypress.PluginEvents, config: Cypress.PluginConfigOptions) {
  codeCoverage(on, config);
  return config;
}
