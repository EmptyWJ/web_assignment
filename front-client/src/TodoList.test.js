import React from "react";
import {
  render,
  fireEvent,
  act,
  wait,
  getByTestId,
  getAllByTestId,
} from "@testing-library/react";
import TodoList from "./TodoList";
import * as TodoApi from "./Todo";

describe("<TodoList>", () => {
  const item = { id: 1, including: "xjtu-se-web", uTime: "2020/04/14" };
  const updateItem = { id: 1, including: "web assignment", uTime: "2020/04/14" };
  const addedItem = { id: 2, including: "xjtu-software", uTime: "2020/04/14" };
  beforeEach(() => {
    jest
      .spyOn(TodoApi, "getTodos")
      .mockImplementation(() => Promise.resolve([item]));
  });

  test("should display todo items correctly", async () => {
    await act(async () => {
      render(<TodoList />);
    });


    expect(getByTestId(document.body, "task-item").textarea=="xjtu-se-web");
  });

  test("should delete todo item correctly", async () => {
    jest
      .spyOn(TodoApi, "deleteTodo")
      .mockImplementation(() => Promise.resolve({}));

    await act(async () => {
      render(<TodoList />);
    });

    act(() => {
      fireEvent.click(getByTestId(document.body, "delete-button"));
    });
    await wait(() => expect(TodoApi.deleteTodo).toHaveBeenCalled());
    expect(getByTestId(document.body, "task-items").length==0);
  });

  test("should edit todo item correctly", async () => {
    jest
      .spyOn(TodoApi, "updateTodo")
      .mockImplementation(() => Promise.resolve(updateItem));

    await act(async () => {
      render(<TodoList />);
    });

    const textarea = document.querySelector("li textarea");
    act(() => {
      fireEvent.click(getByTestId(document.body, "edit-button"));
      fireEvent.change(textarea, {
        target: { value: updateItem.including },
      });
      fireEvent.blur(textarea);
    });

    await wait(() => expect(TodoApi.updateTodo).toHaveBeenCalled());
    expect(textarea.value).toEqual(updateItem.including);
  });
  
  test("should add todo item correctly", async () => {
    jest
      .spyOn(TodoApi, "addTodo")
      .mockImplementation(() => Promise.resolve(addedItem));

    await act(async () => {
      render(<TodoList />);
    });

    act(() => {
      fireEvent.change(getByTestId(document.body, "task-input"), {
        target: { value: addedItem.including },
      });
    });

    act(() => {
      fireEvent.click(getByTestId(document.body, "add-button"));
    });
    await wait(() => expect(TodoApi.addTodo).toHaveBeenCalled());

    const taskItems = getAllByTestId(document.body, "task-item");
    expect(taskItems.length).toEqual(2);
    expect(taskItems[1].textarea==addedItem.including);
  });
});