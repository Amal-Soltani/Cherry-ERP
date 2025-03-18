
export class AddCommentRequest {

  public rtl: boolean;
  public commentText: string;
  public postId: number;

  constructor(values: object = {}) {
    Object.assign(this, values);
  }
}

