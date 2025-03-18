import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class EchartsUtils {

  constructor(private http: HttpClient) {}

  chartPie() {
    return {
      'animation': true,
      'calculable': true,
      'animationEasing': 'elasticOut',
      'tooltip': {
        'trigger': 'item',
        'formatter': '{a} <br/>{b} : {c} ({d}%)'
      },
      'legend': {
        'data': [],
        'bottom': 10,
        'itemGap': 5
      },
      'series': [
        {
          'id': '',
          'name': '',
          'data': [],
          'type': 'pie',
          'radius': [
            '40%',
            '60%'
          ],
          'center': [
            '50%',
            '40%'
          ],
          'label': {
            'normal': {
              'formatter': '{b}\n[{c}] {d}%'
            }
          }
        }
      ]
    };
  }

  barChart() {
    return {
      'theme': 'infographic',
      'animation': true,
      'calculable': true,
      'animationEasing': 'elasticOut',
      'backgroundColor': '',
      'grid': {
        'left': 10,
        'right': 10,
        'bottom': 60,
        'containLabel': true
      },
      'yAxis': [
        {
          'name' : 'NB connexion',
          'type': 'value'
        }
      ],
      'xAxis': [
        {
          'type': 'category',
          'boundaryGap': true,
          'data': []
        }
      ],
      'series': [
        {
          'id': 'nb_nfc',
          'name': 'NB BR',
          'data': [],
          'type': 'bar',
          'label': {
            'normal': {
              'show': true,
              'position': 'top',
              'color': '#555555'
            }
          },
          'markPoint': {
            'data': [
              {
                'type': 'max',
                'symbole': 'circle',
                'name': 'Max',
                'symbol': 'pin'
              }
            ]
          }
        }
      ],
      'legend': {
        'data': [],
        'bottom': 5,
        'itemGap': 6
      },
      'tooltip': {
        'trigger': 'axis',
        'axisPointer': {
          'type': 'cross',
          'label': {
            'backgroundColor': '#283b56'
          }
        }
      },
      'toolbox': {
        'show': true,
        'feature': {
          'dataView': {'show': true,'readOnly': false},
          'magicType': {'show': true,'type': [ 'line','bar']},
          'restore': {'show': true},
          'saveAsImage': { 'show': true},
          'dataZoom': {'yAxisIndex': true}
        }
      },
      'dataZoom': {
        'show': true,'start': 25,'end': 100,'bottom': 25
      }
    };
  }

}
