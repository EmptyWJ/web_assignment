const basePath = (process.env.NODE_ENV === 'production' ? '/todo-list':'');

export const request = (path, params) => {
	let mergedParams = {
	    credentials: 'same-origin',
	    ...params
	 }
	return fetch(basePath+path, mergedParams);
}

export const requestWithBody = (path, method, body) => {
	return request(path, { 
	    headers: {
	      'content-type': 'application/json'
	    },
	    method, 
	    body: JSON.stringify(body)
	})
}

export const deleteItem = (path) => {
	return request(path, { 
	    method: 'delete'
	})
}