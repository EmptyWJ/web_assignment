import {request, requestWithBody, deleteItem} from './Base'

const TODO_PATH = "/api/schedule";

export const getTodos = () => {
     return request(TODO_PATH)
    .then(response => response.json());
}

export const addTodo = (todo) => {
	return requestWithBody(TODO_PATH, 'post', todo);
}

export const updateTodo = (todo) => {
	return requestWithBody(TODO_PATH + "/" + todo.id, 'put', todo)
    .then(response => response.json());
}

export const deleteTodo = (todoId) => {	
    return deleteItem(TODO_PATH + "/" + todoId);
}