package com.hot.parse.entity.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class OperatingResult {
	/// <summary>
	/// 状态
	/// </summary>
	public Integer state;
	/// <summary>
	/// 操作是否成功
	/// </summary>
	public boolean IsSucess;

	private String message = "操作失败!";
	/// <summary>
	/// 操作提示信息
	/// </summary>
	public String Message;

	/// <summary>
	/// 操作返回的对象,只有在成功是才不为空
	/// </summary>
	public Object Data;
}
