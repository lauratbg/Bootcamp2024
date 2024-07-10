import { Inject, Injectable, InjectionToken, Optional } from '@angular/core';
export const ERROR_LEVEL = new InjectionToken<string>('ERROR_LEVEL');
@Injectable()
// {providedIn: 'root'}
export class LoggerService {
  private readonly nivel: number; // en java readonly es final

  constructor(@Optional() @Inject(ERROR_LEVEL) nivel?: number) {
    // if (nivel || nivel === 0) this.nivel = nivel; // aquí se juega con trusti y falsi
    this.nivel = nivel ?? 99; //jugando con undefined
  }

  public error(message: string): void {
    if (this.nivel > 0) console.error(message);
  }
  public warn(message: string): void {
    if (this.nivel > 1) console.warn(message);
  }
  public info(message: string): void {
    if (this.nivel > 2)
      if (console.info) console.info(message);
      else console.log(message);
  }
  public log(message: string): void {
    if (this.nivel > 3) console.log(message);
  }
}
