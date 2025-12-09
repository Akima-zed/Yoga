/// <reference types="jest" />
declare global {
  namespace jest {
    interface Matchers<R> {
      toHaveBeenCalledWith(...args: any[]): R;
      toHaveBeenCalled(): R;
      toBeTruthy(): R;
      toBeUndefined(): R;
      toEqual(expected: any): R;
    }
  }
}
export {};
