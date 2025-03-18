import { Inject, LOCALE_ID, Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'restMsg'
})
export class RestMsgPipe implements PipeTransform {

  constructor(@Inject(LOCALE_ID) public locale: string) {}

  transform(value: any, args?: any): any {
    const restMessages = JSON.parse(sessionStorage.getItem("restMessages"));
    let result = value;
    if (restMessages) {
      const restResponse = restMessages.find(msg => msg.code === value);
      if (restResponse) {
        if (this.locale) {
          let message = restResponse.messages.messages.find(msg => msg.locale === this.locale);
          if (!message) {
            message = restResponse.messages.messages.find(msg => msg.defaultMsg === true)
          }
          if (message) {
            result = message.message;
          }
        }
      }
      return result;
    }
  }

}
