import { Injectable, OnDestroy } from "@angular/core";
import { Subscription } from "rxjs";

@Injectable()
export abstract class BaseSubscriptionHandler implements OnDestroy {

  private _subscription = new Subscription();

  addSubscription(subscription: Subscription): void {
    this._subscription.add(subscription);
  }

  ngOnDestroy(): void {
    if (this._subscription) {
      this._subscription.unsubscribe();
    }
  }

}
