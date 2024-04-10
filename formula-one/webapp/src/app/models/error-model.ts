import { HttpErrorResponse } from '@angular/common/http';

export interface ErrorModel extends HttpErrorResponse {
  error: {
    status: string;
    message: string;
  };
}
