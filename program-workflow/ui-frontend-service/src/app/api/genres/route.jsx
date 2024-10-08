import clientPromise from '../../../../lib/mongodb.jsx';

export async function GET(request) {
  try {
    const client = await clientPromise;
    const db = client.db('masterData');
    const genresCollection = db.collection('genreMasterData');
    

    const genres = await genresCollection.find({}).toArray();
    
    return new Response(JSON.stringify(genres), {
      status: 200,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (error) {
    console.error('Error fetching genres:', error);
    return new Response(JSON.stringify({ error: 'Failed to fetch genres' }), {
      status: 500,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }
}
