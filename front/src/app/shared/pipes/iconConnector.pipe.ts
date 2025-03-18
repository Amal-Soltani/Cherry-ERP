import {Pipe, PipeTransform} from '@angular/core';
@Pipe ({
  name : 'iconConnector'
})
export class IconConnectorPipe implements PipeTransform {

  transform(status: string): string {
    switch (status) {
      case 'MYSQL': { return 'MYSQL.png'; break; }
      case 'MARIADB': { return 'MariaDB-Logo.png'; break; }
      case 'POSTGRES': { return 'Postgresql.svg'; break; }
      case 'ORACLE': { return 'Oracle.svg'; break; }
      case 'SQLSERVER': { return 'icon-sql-server.png'; break; }
      case 'H2': { return 'h2-logo.png'; break; }

      case 'CSV': { return 'icon-csv.png'; break; }
      case 'EXCEL': { return 'Excel.svg'; break; }
      case 'JSON': { return 'icon-json.png'; break; }
      case 'GEOJSON': { return 'geojson.svg'; break; }
      case 'GOOLESHEETS': { return 'Google-Sheets.svg'; break; }

      case 'MONGODB': { return 'Mongodb.svg'; break; }
      case 'CASSENDRA': { return 'apache_cassandra-icon.svg'; break; }
      case 'HIVE': { return 'apache_hive-icon.svg'; break; }
      case 'GREENPLUM': { return 'greenplum.svg'; break; }
      case 'REDIS': { return 'redis-logo.png'; break; }

      case 'API': { return 'api.svg'; break; }
      case 'SAP': { return 'sap-icon.svg'; break; }
      case 'SALESFORCE': { return 'salesforce.svg'; break; }

      case 'MONDRIAN': { return 'Mondrian-Logo.png'; break; }
    }
  }

}
